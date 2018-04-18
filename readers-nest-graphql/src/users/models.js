import RestConnector from '../connector/restConnector';
import { parseResponseFromPromise } from '../connector/responseParser';

const USER_SERVICE_ROOT = 'http://10.0.2.15:9000/users/';

export default class Users {
  constructor({ header }) {
    this.restConnector = new RestConnector(header);
  }

  findAll() {
    return this.restConnector.get(USER_SERVICE_ROOT).then(parseResponseFromPromise);
  }

  findById(userId) {
    return this.restConnector.get(USER_SERVICE_ROOT + userId).then(parseResponseFromPromise);
  }

  signup(userWithPassword) {
    const user = {
      firstName: userWithPassword.firstName,
      lastName: userWithPassword.lastName,
      username: userWithPassword.username,
      email: userWithPassword.email,
    };
    const credentials = {
      password: userWithPassword.password,
    };
    return this.restConnector.post(`${USER_SERVICE_ROOT}signup`, { user, credentials }).then(parseResponseFromPromise);
  }

  login(credentials) {
    return this.restConnector.post(`${USER_SERVICE_ROOT}login`, credentials).then(parseResponseFromPromise);
  }

  refreshToken(refreshToken) {
    return this.restConnector.get(`${USER_SERVICE_ROOT}token/refresh`, {
      token: refreshToken,
    }).then(parseResponseFromPromise);
  }

  updateUser(id, user) {
    return this.restConnector.put(USER_SERVICE_ROOT + id, user).then(parseResponseFromPromise);
  }

  deleteUser(id) {
    return this.restConnector.delete(USER_SERVICE_ROOT + id).then(parseResponseFromPromise);
  }

  addBookToUserHistory(userId, bookId) {
    return this.restConnector.put(`${USER_SERVICE_ROOT + userId}/addBook/${bookId}`).then(parseResponseFromPromise);
  }
}
