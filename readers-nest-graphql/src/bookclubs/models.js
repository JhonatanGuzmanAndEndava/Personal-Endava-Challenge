import RestConnector from '../connector/restConnector';
import { parseHttpResponseFromPromise } from '../connector/responseParser';

const BOOKCLUB_SERVICE_ROOT = 'http://localhost:9002/bookclub';

export default class Bookclubs {
  constructor({ header }) {
    this.restConnector = new RestConnector(header);
  }

  findAll() {
    return this.restConnector.get(BOOKCLUB_SERVICE_ROOT).then(parseHttpResponseFromPromise);
  }

  findById(id) {
    return this.restConnector.get(`${BOOKCLUB_SERVICE_ROOT}/${id}`).then(parseHttpResponseFromPromise);
  }

  create(bookclub) {
    return this.restConnector.post(BOOKCLUB_SERVICE_ROOT, bookclub)
      .then(parseHttpResponseFromPromise);
  }

  update(id, bookclub) {
    return this.restConnector.put(`${BOOKCLUB_SERVICE_ROOT}/${id}`, bookclub)
      .then(parseHttpResponseFromPromise);
  }

  delete(id) {
    return this.restConnector.delete(`${BOOKCLUB_SERVICE_ROOT}/${id}`)
      .then(parseHttpResponseFromPromise);
  }

  getMessages(id) {
    return this.restConnector.get(`${BOOKCLUB_SERVICE_ROOT}/${id}/messages`)
      .then(parseHttpResponseFromPromise);
  }

  postMessage(id, message) {
    return this.restConnector.post(`${BOOKCLUB_SERVICE_ROOT}/${id}/messages`, { contentMessage: message })
      .then(parseHttpResponseFromPromise);
  }

  getAdmins(id) {
    return this.restConnector.get(`${BOOKCLUB_SERVICE_ROOT}/${id}/admins`)
      .then(parseHttpResponseFromPromise);
  }

  addAdmin(bookclubId, adminId) {
    return this.restConnector.post(`${BOOKCLUB_SERVICE_ROOT}/${bookclubId}/admins`, { adminId })
      .then(parseHttpResponseFromPromise);
  }

  removeAdmin(bookclubId, adminId) {
    return this.restConnector.delete(`${BOOKCLUB_SERVICE_ROOT}/${bookclubId}/admins/${adminId}`)
      .then(parseHttpResponseFromPromise);
  }

  getMembers(id) {
    return this.restConnector.get(`${BOOKCLUB_SERVICE_ROOT}/${id}/members`)
      .then(parseHttpResponseFromPromise);
  }

  addMember(bookclubId, memberId) {
    return this.restConnector.post(`${BOOKCLUB_SERVICE_ROOT}/${bookclubId}/members`, { memberId })
      .then(parseHttpResponseFromPromise);
  }

  removeMember(bookclubId, memberId) {
    return this.restConnector.delete(`${BOOKCLUB_SERVICE_ROOT}/${bookclubId}/members/${memberId}`)
      .then(parseHttpResponseFromPromise);
  }
}
