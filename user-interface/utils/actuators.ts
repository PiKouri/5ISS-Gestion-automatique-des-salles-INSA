/* eslint-disable @typescript-eslint/no-explicit-any */
import { ActuatorProps } from '../types/ActuatorProps';
import { jsonFetcher } from '../constants/links';

export async function waitForActuatorStatus(
  props: ActuatorProps
): Promise<any> {
  try {
    return await jsonFetcher(props.url + props.id);
  } catch (e) {
    return null;
  }
}

export function deleteActuator(props: ActuatorProps) {
  try {
    fetch(props.url + props.id, {
      method: 'DELETE',
    }).then(() => {
      props.onDelete();
    });
  } catch (e) {}
}

export function on(props: ActuatorProps) {
  return fetch(props.url + props.id + props.onUrl, {
    method: 'POST',
  });
}

export function off(props: ActuatorProps) {
  return fetch(props.url + props.id + props.offUrl, {
    method: 'POST',
  });
}
