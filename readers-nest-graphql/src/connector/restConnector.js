import rp from 'request-promise';

export default class RestServiceConnnector {
  constructor({ header } = {}) {
    this.header = header;
    this.rp = rp;
  }

  get(url) {
    const options = {
      uri: url,
      json: true,
    };
    if (this.header) {
      options.headers = {
        Authorization: this.header,
      };
    }

    return this.rp(options);
  }

  post(url, body) {
    const options = {
      uri: url,
      method: 'POST',
      body,
      json: true,
    };
    if (this.header) {
      options.headers = {
        Authorization: this.header,
      };
    }

    return this.rp(options);
  }

  put(url, body) {
    const options = {
      uri: url,
      method: 'PUT',
      body,
      json: true,
    };
    if (this.header) {
      options.headers = {
        Authorization: this.header,
      };
    }

    return this.rp(options);
  }

  delete(url) {
    const options = {
      uri: url,
      method: 'DELETE',
      json: true,
    };
    if (this.header) {
      options.headers = {
        Authorization: this.header,
      };
    }

    return this.rp(options);
  }
}
