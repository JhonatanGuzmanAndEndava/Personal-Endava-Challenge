import React from 'react';
import PropTypes from 'prop-types';

const BookInfo = ({ book }) => (
  <div className="ui centered card">
    <div className="image">
      <img src="http://www.bernunlimited.com/c.4436185/sca-dev-vinson/img/no_image_available.jpeg" alt="Book cover" />
    </div>
    <div className="content">
      <h1 className="header">{book.name}</h1>
      <div className="meta">
        <span className="date">Published in {book.publishingDate}</span>
      </div>
      <div className="description">
        <p>Author: {book.author}</p>
        <p>Editorial: {book.editorial}</p>
      </div>
    </div>
  </div>
);

BookInfo.propTypes = {
  book: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    author: PropTypes.string,
    editorial: PropTypes.string,
    publishingDate: PropTypes.string,
  }).isRequired,
};

export default BookInfo;
