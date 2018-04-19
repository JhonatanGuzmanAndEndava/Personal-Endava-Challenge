import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

const BookListElem = props => (
  <div className="item" >
    <div className="content">
      <Link to={`/books/${props.book.id}`} className="header">{props.book.name}</Link>
      <div className="description">{props.book.author}</div>
    </div>
  </div>
);


BookListElem.propTypes = {
  book: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    author: PropTypes.string,
  }).isRequired,
};

export default BookListElem;
