import { SensorProps } from '../types/SensorProps';
import { jsonFetcher } from '../constants/links';

export async function waitForSensorValue(props: SensorProps): Promise<number> {
  try {
    return await jsonFetcher(props.url + props.id);
  } catch (e) {
    return null;
  }
}

export function updateValue(props: SensorProps, value: number) {
  return fetch(props.url + props.id, {
    method: 'PUT',
    body: value.toString(),
    headers: {
      'Content-Type': 'application/json',
    },
  });
}

export function deleteSensor(props: SensorProps) {
  try {
    fetch(props.url + props.id, {
      method: 'DELETE',
    }).then(() => {
      props.onDelete();
    });
  } catch (e) {}
}

export function updateInputValueNumber(
  evt,
  setInputValue: (value: ((prevState: number) => number) | number) => void
) {
  const val = evt.target.value;
  setInputValue(val);
}
