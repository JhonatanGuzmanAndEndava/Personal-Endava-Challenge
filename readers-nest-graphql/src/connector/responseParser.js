export default function parseResponseFromPromise(promiseResult) {
  if (promiseResult.error) {
    const { error } = promiseResult;
    throw new Error(error.description);
  } else {
    return promiseResult.data;
  }
}
