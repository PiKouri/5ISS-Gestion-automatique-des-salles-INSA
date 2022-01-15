import React, { useState } from 'react';
import Links, { fetcher } from '../constants/links';

function getManagerStatus(
  setState: (value: ((prevState: boolean) => boolean) | boolean) => void
) {
  React.useEffect(() => {
    componentDidMount().then((value) => {
      // eslint-disable-next-line react/no-direct-mutation-state
      setState(value);
    });
  });
}

async function componentDidMount(): Promise<boolean> {
  try {
    return await fetcher(Links.centralManager);
  } catch (e) {
    return false;
  }
}

function startManager() {
  try {
    fetch(Links.centralManager + 'start', {
      method: 'POST',
    });
  } catch (e) {}
}

function stopManager() {
  try {
    fetch(Links.centralManager + 'stop', {
      method: 'POST',
    });
  } catch (e) {}
}

export default function CentralManager() {
  const [running, setState] = useState(false);
  getManagerStatus(setState);
  return (
    <div className={'card'}>
      Central Manager {running ? 'is running' : 'is not running'}{' '}
      <button onClick={() => startManager()}>Start</button>{' '}
      <button onClick={() => stopManager()}>Stop</button>
    </div>
  );
}
