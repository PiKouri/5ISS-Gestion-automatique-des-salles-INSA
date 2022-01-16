import Links, { jsonFetcher, textFetcher } from '../constants/links';
import React, { useState } from 'react';
import { RoomProps } from './Room';
import DeviceTypes from '../constants/deviceTypes';
import Select from 'react-select';
import NumericSensor from './NumericSensor';
import Actuator from './Actuator';

const deviceTypesList = [
  { value: DeviceTypes.lightSensor, label: 'Light Sensor' },
  { value: DeviceTypes.temperatureSensor, label: 'Temperature Sensor' },
  { value: DeviceTypes.gazSensor, label: 'Gaz Sensor' },
  { value: DeviceTypes.co2Sensor, label: 'CO2 Sensor' },
  { value: DeviceTypes.window, label: 'Window' },
  { value: DeviceTypes.door, label: 'Door' },
  { value: DeviceTypes.light, label: 'Light' },
  { value: DeviceTypes.heating, label: 'Heating System' },
  { value: DeviceTypes.cooling, label: 'Cooling System' },
  { value: DeviceTypes.alarm, label: 'Alarm' },
];

async function componentDidMount(
  props: RoomProps,
  type: string
): Promise<number[]> {
  try {
    return await jsonFetcher(Links.rooms + props.id + '/devices?type=' + type);
  } catch (e) {
    return [];
  }
}

function addDevice(
  props: RoomProps,
  type: { value: string; label: string }
): Promise<string> {
  let url;
  switch (type.value) {
    case DeviceTypes.lightSensor:
      url = Links.lightSensor;
      break;
    case DeviceTypes.temperatureSensor:
      url = Links.temperatureSensor;
      break;
    case DeviceTypes.gazSensor:
      url = Links.gazSensor;
      break;
    case DeviceTypes.co2Sensor:
      url = Links.co2Sensor;
      break;
    case DeviceTypes.window:
      url = Links.window;
      break;
    case DeviceTypes.door:
      url = Links.door;
      break;
    case DeviceTypes.light:
      url = Links.light;
      break;
    case DeviceTypes.heating:
      url = Links.heating;
      break;
    case DeviceTypes.cooling:
      url = Links.cooling;
      break;
    case DeviceTypes.alarm:
      url = Links.alarm;
      break;
  }
  return new Promise<string>((resolve) => {
    fetch(url, {
      method: 'POST',
    }).then((res) => {
      res.text().then((id) =>
        fetch(Links.rooms + props.id + '/devices?type=' + type.value, {
          method: 'POST',
          body: JSON.parse(id),
          headers: {
            'Content-Type': 'application/json',
          },
        }).then(() => resolve(''))
      );
    });
  });
}

function deleteDevice(props: RoomProps, type: string, id: string) {
  return fetch(Links.rooms + props.id + '/devices/' + id + '?type=' + type, {
    method: 'DELETE',
  });
}

export default function DevicesList(props: RoomProps) {
  const [selectedType, selectType] = useState(deviceTypesList[0]);
  const [lightSensors, setLightSensors] = useState([]);
  const [temperatureSensors, setTemperatureSensors] = useState([]);
  const [gazSensors, setGazSensors] = useState([]);
  const [co2Sensors, setCo2Sensors] = useState([]);
  const [windows, setWindows] = useState([]);
  const [doors, setDoors] = useState([]);
  const [lights, setLights] = useState([]);
  const [heatings, setHeatings] = useState([]);
  const [coolings, setCoolings] = useState([]);
  const [alarms, setAlarms] = useState([]);

  const updateLightSensors = () => {
    componentDidMount(props, DeviceTypes.lightSensor).then((value) =>
      setLightSensors(value)
    );
  };

  const updateTemperatureSensors = () => {
    componentDidMount(props, DeviceTypes.temperatureSensor).then((value) =>
      setTemperatureSensors(value)
    );
  };

  const updateGazSensors = () => {
    componentDidMount(props, DeviceTypes.gazSensor).then((value) =>
      setGazSensors(value)
    );
  };

  const updateCo2Sensors = () => {
    componentDidMount(props, DeviceTypes.co2Sensor).then((value) =>
      setCo2Sensors(value)
    );
  };

  const updateWindows = () => {
    componentDidMount(props, DeviceTypes.window).then((value) =>
      setWindows(value)
    );
  };

  const updateDoors = () => {
    componentDidMount(props, DeviceTypes.door).then((value) => setDoors(value));
  };

  const updateLights = () => {
    componentDidMount(props, DeviceTypes.light).then((value) =>
      setLights(value)
    );
  };

  const updateHeating = () => {
    componentDidMount(props, DeviceTypes.heating).then((value) =>
      setHeatings(value)
    );
  };

  const updateCooling = () => {
    componentDidMount(props, DeviceTypes.cooling).then((value) =>
      setCoolings(value)
    );
  };

  const updateAlarms = () => {
    componentDidMount(props, DeviceTypes.alarm).then((value) =>
      setAlarms(value)
    );
  };

  const updateAll = () => {
    updateLightSensors();
    updateTemperatureSensors();
    updateGazSensors();
    updateCo2Sensors();
    updateWindows();
    updateDoors();
    updateLights();
    updateHeating();
    updateCooling();
    updateAlarms();
  };

  React.useEffect(() => {
    updateAll();
  }, []);
  return (
    <div className={'card'}>
      <h4>Devices list :</h4>
      <br />
      Light Sensors:
      {lightSensors.map((item, index) => {
        return (
          <NumericSensor
            key={index}
            id={item}
            name={'Light Sensor'}
            url={Links.lightSensor}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.lightSensor, item).then(() =>
                updateLightSensors()
              );
            }}
          />
        );
      })}
      <br />
      Temperature Sensors:
      {temperatureSensors.map((item, index) => {
        return (
          <NumericSensor
            key={index}
            id={item}
            name={'Temperature Sensor'}
            url={Links.temperatureSensor}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.temperatureSensor, item).then(
                () => updateTemperatureSensors()
              );
            }}
          />
        );
      })}
      <br />
      Gaz Sensors:
      {gazSensors.map((item, index) => {
        return (
          <NumericSensor
            key={index}
            id={item}
            name={'Gaz Sensor'}
            url={Links.gazSensor}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.gazSensor, item).then(() =>
                updateGazSensors()
              );
            }}
          />
        );
      })}
      <br />
      CO2 Sensors:
      {co2Sensors.map((item, index) => {
        return (
          <NumericSensor
            key={index}
            id={item}
            name={'CO2 Sensor'}
            url={Links.co2Sensor}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.co2Sensor, item).then(() =>
                updateCo2Sensors()
              );
            }}
          />
        );
      })}
      <br />
      Windows:
      {windows.map((item, index) => {
        return (
          <Actuator
            key={index}
            id={item}
            name={'Window'}
            url={Links.window}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.window, item).then(() =>
                updateWindows()
              );
            }}
            offButton={'Close'}
            offUrl={'/close'}
            off={'is closed'}
            onButton={'Open'}
            onUrl={'/open'}
            on={'is opened'}
          />
        );
      })}
      <br />
      Doors:
      {doors.map((item, index) => {
        return (
          <Actuator
            key={index}
            id={item}
            name={'Door'}
            url={Links.door}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.door, item).then(() =>
                updateDoors()
              );
            }}
            offButton={'Close'}
            offUrl={'/close'}
            off={'is closed'}
            onButton={'Open'}
            onUrl={'/open'}
            on={'is opened'}
          />
        );
      })}
      <br />
      Lights:
      {lights.map((item, index) => {
        return (
          <Actuator
            key={index}
            id={item}
            name={'Light'}
            url={Links.light}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.light, item).then(() =>
                updateLights()
              );
            }}
            offButton={'Turn off'}
            offUrl={'/turnOff'}
            off={'is off'}
            onButton={'Turn on'}
            onUrl={'/turnOn'}
            on={'is on'}
          />
        );
      })}
      <br />
      Heating Systems:
      {heatings.map((item, index) => {
        return (
          <Actuator
            key={index}
            id={item}
            name={'Heating System'}
            url={Links.heating}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.heating, item).then(() =>
                updateHeating()
              );
            }}
            offButton={'Turn off'}
            offUrl={'/turnOff'}
            off={'is off'}
            onButton={'Turn on'}
            onUrl={'/turnOn'}
            on={'is on'}
          />
        );
      })}
      <br />
      Cooling Systems:
      {coolings.map((item, index) => {
        return (
          <Actuator
            key={index}
            id={item}
            name={'Cooling System'}
            url={Links.cooling}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.cooling, item).then(() =>
                updateCooling()
              );
            }}
            offButton={'Turn off'}
            offUrl={'/turnOff'}
            off={'is off'}
            onButton={'Turn on'}
            onUrl={'/turnOn'}
            on={'is on'}
          />
        );
      })}
      <br />
      Alarms:
      {alarms.map((item, index) => {
        return (
          <Actuator
            key={index}
            id={item}
            name={'Alarm'}
            url={Links.alarm}
            onDelete={() => {
              deleteDevice(props, DeviceTypes.alarm, item).then(() =>
                updateAlarms()
              );
            }}
            offButton={'Turn off'}
            offUrl={'/turnOff'}
            off={'is off'}
            onButton={'Turn on'}
            onUrl={'/turnOn'}
            on={'is on'}
          />
        );
      })}
      <div className={'card'}>
        <p className={'p-inline'}>Add device : </p>
        <Select
          defaultValue={selectedType}
          onChange={selectType}
          options={deviceTypesList}
        />
        <button
          onClick={() => {
            addDevice(props, selectedType).then(() => {
              updateAll();
            });
          }}
        >
          Add
        </button>
      </div>
    </div>
  );
}
