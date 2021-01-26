import { useEffect, useRef, useCallback, useContext } from "react";
import fetch from "isomorphic-unfetch";
import AuthenticationContext from "../auth/AuthenticationContext";
import logout from "../auth/logout";
import RequestMethod from "./RequestMethod";

const useFetch = () => {
  const { token } = useContext(AuthenticationContext);
  const abortController = useRef(new AbortController());

  useEffect(() => {
    const controller = abortController.current;

    return () => {
      controller.abort();
    };
  }, []);

  const cleanFetch = useCallback(
    (endpoint, { headers: headersWithoutToken, ...init } = {}) => {
      return new Promise((resolve, reject) => {
        const headers = { ...headersWithoutToken, Authentication: token ? `Bearer ${token}` : null };
        const { signal } = abortController.current;
        const requestInit = { ...init, signal, headers };

        fetch(endpoint, requestInit)
          .then((response) => {
            if (response.status === 401) {
              logout();
            } else {
              resolve(response);
            }
          })
          .catch((error) => reject(error));
      });
    },
    [token]
  );

  const fetchJSON = useCallback(
    (method, endpoint, entity) => {
      return new Promise((resolve, reject) => {
        const requestInit = {
          method,
          cache: "no-cache",
          credentials: "include",
          headers: {
            Accept: "application/json",
            ...(entity && { "Content-Type": "application/json" }),
          },
          ...(entity && { body: JSON.stringify(entity) }),
        };

        cleanFetch(endpoint, requestInit)
          .then((response) => {
            response
              .json()
              .then((json) => {
                if (response.ok) {
                  resolve(json);
                } else {
                  reject(json.message);
                }
              })
              .catch((error) => reject(error));
          })
          .catch((error) => reject(error));
      });
    },
    [cleanFetch]
  );

  const getJSON = useCallback((endpoint) => fetchJSON(RequestMethod.GET, endpoint), [fetchJSON]);

  const postJSON = useCallback((endpoint, entity) => fetchJSON(RequestMethod.POST, endpoint, entity), [fetchJSON]);

  const putJSON = useCallback((endpoint, entity) => fetchJSON(RequestMethod.PUT, endpoint, entity), [fetchJSON]);

  const deleteJSON = useCallback((endpoint) => fetchJSON(RequestMethod.DELETE, endpoint), [fetchJSON]);

  return { fetch: cleanFetch, fetchJSON, getJSON, postJSON, putJSON, deleteJSON };
};

export default useFetch;
