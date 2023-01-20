package com.code.OpenWeather;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import com.code.OpenWeather.model.CurrentWeather;
import com.code.OpenWeather.utilitaire.Alert;
import com.code.OpenWeather.utilitaire.Api;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainFrame extends JFrame {

  private static final long serialVersionUID = 5783356846557956361L;

  private static final String GENERIC_ERROR_MESSAGE = "Oupsss, une erreur est survenue";

  private CurrentWeather currentWeather;

  private static final Color BLUE_COLOR = Color.decode("#8EA2C6");
  private static final Color WHITE_COLOR = Color.WHITE;
  private static final Color LIGHT_GRAY_COLOR = new Color(255, 255, 255, 128);
  private static final Font DEFAULT_FONT = new Font("Proxima Nova", Font.PLAIN, 24);

  private JLabel locationLabel;
  private JLabel timeLabel;
  private JLabel temperatureLabel;
  private JPanel otherInfoPanel;
  private JLabel humidityLabel;
  private JLabel humidityValue;
  private JLabel precipLabel;
  private JLabel precipValue;
  private JLabel descriptionLabel;


  public MainFrame(String title) {
    super(title);


    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
    contentPane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
    contentPane.setBackground(BLUE_COLOR);


    locationLabel = new JLabel("Douala, CM", SwingConstants.CENTER);
    locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    locationLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    locationLabel.setFont(DEFAULT_FONT);
    locationLabel.setForeground(WHITE_COLOR);


    timeLabel = new JLabel("...", SwingConstants.CENTER);
    timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    timeLabel.setFont(DEFAULT_FONT.deriveFont(18f));
    timeLabel.setForeground(LIGHT_GRAY_COLOR);


    // temperatureLabel = new JLabel("100°", SwingConstants.CENTER);
    temperatureLabel = new JLabel("--", SwingConstants.CENTER);
    temperatureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    temperatureLabel.setForeground(WHITE_COLOR);
    temperatureLabel.setFont(DEFAULT_FONT.deriveFont(160f));


    otherInfoPanel = new JPanel();
    otherInfoPanel.setBackground(BLUE_COLOR);
    otherInfoPanel.setLayout(new GridLayout(2, 2));


    humidityLabel = new JLabel("Humidité".toUpperCase(), SwingConstants.CENTER);
    humidityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    humidityLabel.setForeground(LIGHT_GRAY_COLOR);

    humidityValue = new JLabel("--", SwingConstants.CENTER);
    humidityValue.setAlignmentX(Component.CENTER_ALIGNMENT);
    humidityValue.setForeground(WHITE_COLOR);
    humidityValue.setFont(DEFAULT_FONT);

    precipLabel = new JLabel("Risque de pluie".toUpperCase(), SwingConstants.CENTER);
    precipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    precipLabel.setForeground(LIGHT_GRAY_COLOR);

    precipValue = new JLabel("--", SwingConstants.CENTER);
    precipValue.setAlignmentX(Component.CENTER_ALIGNMENT);
    precipValue.setForeground(WHITE_COLOR);
    precipValue.setFont(DEFAULT_FONT);

    otherInfoPanel.add(humidityLabel);
    otherInfoPanel.add(precipLabel);
    otherInfoPanel.add(humidityValue);
    otherInfoPanel.add(precipValue);


    descriptionLabel = new JLabel("Nuages Epars");
    descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    descriptionLabel.setForeground(WHITE_COLOR);
    descriptionLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
    descriptionLabel.setFont(DEFAULT_FONT.deriveFont(14f));

    contentPane.add(locationLabel);
    contentPane.add(timeLabel);
    contentPane.add(temperatureLabel);
    contentPane.add(otherInfoPanel);
    contentPane.add(descriptionLabel);


    setContentPane(contentPane);


    String city = "Douala";
    /*
     * String farecastUrl = String
     * .format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, apiKey);
     */


    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(Api.getForecastUrl(city)).build();

    Call call = client.newCall(request);

    call.enqueue(new Callback() {

      @Override
      public void onResponse(Call call, Response response) {
        try (ResponseBody body = response.body()) {
          if (response.isSuccessful()) {
            String jsonData = body.string();

            currentWeather = getCurrentWeatherDetails(jsonData);
            // System.out.println(currentWeather.getFormattedTime());

            try {
              Thread.sleep(5000);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }


            EventQueue.invokeLater(new Runnable() {

              @Override
              public void run() {

                updateScreen();
              }
            });
          } else {
            Alert.error(MainFrame.this, GENERIC_ERROR_MESSAGE);
          }
        } catch (ParseException | IOException e) {
          Alert.error(MainFrame.this, GENERIC_ERROR_MESSAGE);

        }

      }

      @Override
      public void onFailure(Call call, IOException e) {
        Alert.error(MainFrame.this, "Vérifier que vous êtes connectés à Internet");

      }
    });

  }

  private void updateScreen() {
    timeLabel.setText(
        "Il est " + currentWeather.getFormattedTime() + " et la température actuelle est de :");
    temperatureLabel.setText(currentWeather.getTemperature() + "°");
    humidityValue.setText(currentWeather.getHumidity() + "");
    precipValue.setText(currentWeather.getPrecipProbability() + "%");
    descriptionLabel.setText(currentWeather.getDescription());
  }

  private CurrentWeather getCurrentWeatherDetails(String jsonData) throws ParseException {
    CurrentWeather currentWeather = new CurrentWeather();
    JSONObject forecast = (JSONObject) JSONValue.parseWithException(jsonData);

    currentWeather.setName((String) forecast.get("name"));

    JSONObject main = (JSONObject) forecast.get("main");
    currentWeather.setTemperature(Double.parseDouble(main.get("temp") + ""));
    currentWeather.setHumidity(Double.parseDouble(main.get("humidity") + ""));
    currentWeather.setPrecipProbability(Double.parseDouble(main.get("feels_like") + ""));
    currentWeather.setTime((long) forecast.get("dt"));

    JSONArray weather = (JSONArray) forecast.get("weather");
    JSONObject weather1 = (JSONObject) weather.get(0);
    currentWeather.setDescription((String) weather1.get("description"));

    return currentWeather;
  }

  public Dimension getPreferredSize() {
    return new Dimension(500, 500);
  }

  @Override
  public Dimension getMinimumSize() {
    // TODO Auto-generated method stub
    return getPreferredSize();
  }

  @Override
  public Dimension getMaximumSize() {
    // TODO Auto-generated method stub
    return getPreferredSize();
  }

}
