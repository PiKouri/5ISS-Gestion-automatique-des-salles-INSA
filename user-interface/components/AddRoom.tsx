import React, { useState } from 'react';
import Links from '../constants/links';
import Room from './Room';

function addRoom(name: string) {
  return fetch(Links.rooms, {
    method: 'POST',
    body: name,
  });
}

function updateInputValue(
  evt,
  setRoomName: (value: ((prevState: string) => string) | string) => void
) {
  const val = evt.target.value;
  setRoomName(val);
}

export default function AddRoom() {
  const [roomName, setRoomName] = useState('');
  const [addedRooms, setAddedRooms] = useState([]);
  return (
    <>
      {addedRooms.map((item, index) => {
        return <Room key={index} id={item} />;
      })}
      <div className={'card'}>
        <p className={'p-inline'}>Add room : </p>
        <input
          type={'text'}
          value={roomName}
          onChange={(evt) => updateInputValue(evt, setRoomName)}
        />
        <button
          onClick={() => {
            addRoom(roomName).then((res) => {
              res.text().then((id) => {
                const tmp = [...addedRooms];
                console.log(id);
                tmp.push(+id);
                setAddedRooms(tmp);
              });
            });
          }}
        >
          Add room
        </button>
      </div>
    </>
  );
}
