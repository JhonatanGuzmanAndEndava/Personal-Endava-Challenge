import RestConnector from '../connector/restConnector';
import { parseResponseFromPromise } from '../connector/responseParser';

const BOOK_SERVICE_ROOT = 'http://172.31.66.141:9001/books/';

export default class Books {
  constructor({ header }) {
    this.restConnector = new RestConnector(header);
  }

  findAll() {
    return this.restConnector.get(BOOK_SERVICE_ROOT).then(parseResponseFromPromise);
  }

  findById(id) {
    return this.restConnector.get(BOOK_SERVICE_ROOT + id).then(parseResponseFromPromise);
  }

  getReviews(id) {
    return this.restConnector.get(`${BOOK_SERVICE_ROOT + id}/reviews`).then(parseResponseFromPromise);
  }

  createReview(bookId, review) {
    return this.restConnector.post(`${BOOK_SERVICE_ROOT + bookId}/reviews`, review).then(parseResponseFromPromise);
  }

  updateReview(bookId, review) {
    return this.restConnector.put(`${BOOK_SERVICE_ROOT + bookId}/reviews`, review).then(parseResponseFromPromise);
  }

  deleteReview(bookId, userId) {
    return this.restConnector.delete(`${BOOK_SERVICE_ROOT + bookId}/reviews/${userId}`).then(parseResponseFromPromise);
  }
}
