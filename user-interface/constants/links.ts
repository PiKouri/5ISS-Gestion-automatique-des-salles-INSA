const defaultUrl = 'http://localhost';

export default {
  centralManager: defaultUrl + ':8092/rooms/',
  rooms: defaultUrl + ':8081/rooms/',
  lightSensor: defaultUrl + ':8082/lightSensor/',
  temperatureSensor: defaultUrl + ':8083/temperatureSensor/',
  gazSensor: defaultUrl + ':8084/gazSensor/',
  co2Sensor: defaultUrl + ':8085/co2Sensor/',
  window: defaultUrl + ':8086/window/',
  door: defaultUrl + ':8087/door/',
  light: defaultUrl + ':8088/light/',
  heating: defaultUrl + ':8089/heating/',
  cooling: defaultUrl + ':8090/cooling/',
  alarm: defaultUrl + ':8091/alarm/',
};

export const fetcher = (url: string) => fetch(url).then((res) => res.json());
