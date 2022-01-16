import Links, { jsonFetcher, textFetcher } from '../constants/links';
import React, { useState } from 'react';
import DevicesList from './DevicesList';

export type RoomProps = {
  id: number;
};

async function componentDidMount(props: RoomProps): Promise<string> {
  try {
    return await textFetcher(Links.rooms + props.id);
  } catch (e) {
    return '';
  }
}

async function getNbPersons(props: RoomProps): Promise<number> {
  try {
    return await jsonFetcher(Links.rooms + props.id + '/persons');
  } catch (e) {
    return null;
  }
}

function deleteRoom(
  id: number,
  setDeleted: (value: ((prevState: boolean) => boolean) | boolean) => void
) {
  try {
    fetch(Links.rooms + id, {
      method: 'DELETE',
    }).then(() => {
      setDeleted(true);
    });
  } catch (e) {}
}

function addPerson(id: number) {
  try {
    return fetch(Links.rooms + id + '/persons/add', {
      method: 'POST',
      body: '1',
      headers: {
        'Content-Type': 'application/json',
      },
    });
  } catch (e) {}
}

function removePerson(id: number) {
  try {
    return fetch(Links.rooms + id + '/persons/remove', {
      method: 'POST',
      body: '1',
      headers: {
        'Content-Type': 'application/json',
      },
    });
  } catch (e) {
    return null;
  }
}

export default function Room(props: RoomProps) {
  const [name, setName] = useState('');
  const [nbPersons, setNbPersons] = useState(null);
  const [deleted, setDeleted] = useState(false);
  const updateNbPersons = () => {
    getNbPersons(props).then((value) => setNbPersons(value));
  };
  React.useEffect(() => {
    componentDidMount(props).then((value) => setName(value));
    updateNbPersons();
  }, []);
  if (!deleted) {
    return (
      <div className={'card'}>
        <p className={'p-inline'}>
          {name} (id={props.id}) :{' '}
          <p className={'p-inline p-value'}>{nbPersons}</p> are in the room{' '}
        </p>
        <button onClick={() => deleteRoom(props.id, setDeleted)}>
          Remove room
        </button>
        <button
          onClick={() => {
            addPerson(props.id).then(() => updateNbPersons());
          }}
        >
          +
        </button>
        <button
          onClick={() => {
            removePerson(props.id).then(() => updateNbPersons());
          }}
        >
          -
        </button>
        <br />
        <DevicesList id={props.id} />
      </div>
    );
  } else {
    return <div />;
  }
}
