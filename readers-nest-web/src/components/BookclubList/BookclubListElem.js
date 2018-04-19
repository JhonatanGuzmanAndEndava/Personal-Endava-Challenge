import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

const BookclubListElem = ({ bookclub }) => (
  <div className="item" >
    <div className="content">
      <Link to={`/bookclubs/${bookclub.id}`} className="header">{bookclub.name}</Link>
      <div className="description">{bookclub.description}</div>
    </div>
  </div>
);


BookclubListElem.propTypes = {
  bookclub: PropTypes.shape({
    id: PropTypes.number,
    name: PropTypes.string,
    description: PropTypes.string,
  }).isRequired,
};

export default BookclubListElem;
