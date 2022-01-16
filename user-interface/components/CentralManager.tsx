import React, { useState } from 'react';
import Links, { textFetcher } from '../constants/links';

function getManagerStatus(
  setState: (value: ((prevState: string) => string) | string) => void
) {
  componentDidMount().then((value) => {
    setState(value);
  });
}

async function componentDidMount(): Promise<string> {
  try {
    return await textFetcher(Links.centralManager);
  } catch (e) {
    return '';
  }
}

function startManager(
  setState: (value: ((prevState: string) => string) | string) => void
) {
  try {
    fetch(Links.centralManager + 'start', {
      method: 'POST',
    }).then(() => {
      getManagerStatus(setState);
    });
  } catch (e) {}
}

function stopManager(
  setState: (value: ((prevState: string) => string) | string) => void
) {
  try {
    fetch(Links.centralManager + 'stop', {
      method: 'POST',
    }).then(() => {
      getManagerStatus(setState);
    });
  } catch (e) {}
}

export default function CentralManager() {
  const [running, setState] = useState('');
  React.useEffect(() => {
    getManagerStatus(setState);
  });
  return (
    <div className={'card'}>
      Central Manager{' '}
      <p
        className={
          'p-inline ' + (running.includes('is running') ? 'p-on' : 'p-off')
        }
      >
        {running.includes('is running') ? 'is running' : 'is not running'}
      </p>{' '}
      <button onClick={() => startManager(setState)}>Start</button>{' '}
      <button onClick={() => stopManager(setState)}>Stop</button>
    </div>
  );
}
