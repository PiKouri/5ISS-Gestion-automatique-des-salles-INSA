import React, { useState } from 'react';
import { SensorProps } from '../types/SensorProps';
import {
  deleteSensor,
  updateInputValueNumber,
  updateValue,
  waitForSensorValue,
} from '../utils/sensors';

export default function NumericSensor(props: SensorProps) {
  const [value, setValue] = useState(null);
  const [newValue, setNewValue] = useState(0);

  const getSensorValue = () => {
    waitForSensorValue(props).then((val) => setValue(val));
  };

  React.useEffect(() => {
    getSensorValue();
  }, []);
  return (
    <div className={'card'}>
      <p className={'p-inline'}>
        {props.name} (id={props.id}) current value :{' '}
        <p className={'p-inline p-value'}>{value}</p>{' '}
      </p>
      <input
        type={'number'}
        value={newValue}
        onChange={(evt) => updateInputValueNumber(evt, setNewValue)}
      />
      <button
        onClick={() =>
          updateValue(props, newValue).then(() => getSensorValue())
        }
      >
        Set value{' '}
      </button>
      <button onClick={() => deleteSensor(props)}>Remove device</button>
      <br />
    </div>
  );
}
