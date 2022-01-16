import React, { useState } from 'react';
import {
  deleteActuator,
  off,
  on,
  waitForActuatorStatus,
} from '../utils/actuators';
import { ActuatorProps } from '../types/ActuatorProps';

export default function Actuator(props: ActuatorProps) {
  const [status, setStatus] = useState(null);

  const getActuatorStatus = () => {
    waitForActuatorStatus(props).then((val: boolean) => setStatus(val));
  };

  const intervalId = setInterval(() => {
    getActuatorStatus();
  }, 1000);

  React.useEffect(() => {
    getActuatorStatus();
  }, []);
  return (
    <div className={'card'}>
      <p className={'p-inline'}>
        {props.name} (id={props.id}){' '}
        <p className={'p-inline ' + (status ? 'p-on' : 'p-off')}>
          {status ? props.on : props.off}
        </p>{' '}
      </p>
      <button onClick={() => on(props).then(() => getActuatorStatus())}>
        {props.onButton}
      </button>
      <button onClick={() => off(props).then(() => getActuatorStatus())}>
        {props.offButton}
      </button>
      <button
        onClick={() => {
          deleteActuator(props);
          clearInterval(intervalId);
        }}
      >
        Remove device
      </button>
      <br />
    </div>
  );
}
