import { isArray, flatMap, isPlainObject, keys, map, concat } from "lodash";
import yup from "./yup";

export const isRequired = (validationSchema, name) =>
  yup
    .reach(validationSchema, name)
    .describe()
    .tests.some(({ name: testName }) => testName === "required");

export const getPaths = (obj, parentKey) => {
  let result;

  if (isArray(obj)) {
    let idx = 0;
    // eslint-disable-next-line no-plusplus
    result = flatMap(obj, (obj2) => getPaths(obj2, `${parentKey || ""}[${idx++}]`));
  } else if (isPlainObject(obj)) {
    result = flatMap(keys(obj), (key) =>
      map(getPaths(obj[key], key), (subkey) => (parentKey ? `${parentKey}.` : "") + subkey)
    );
  } else {
    result = [];
  }

  return concat(result, parentKey || []);
};
