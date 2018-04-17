export function parseResponseFromPromise(promiseResult) {
  if (promiseResult.error) {
    const { error } = promiseResult;
    throw new Error(error.description);
  } else {
    return promiseResult.data;
  }
}

export function parseHttpResponseFromPromise(promiseResult) {
  if (promiseResult.error) {
    const { error } = promiseResult;
    throw new Error(error.description);
  } else {
    return promiseResult.data;
  }
}
