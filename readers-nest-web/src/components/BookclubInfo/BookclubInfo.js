import React from 'react';
import PropTypes from 'prop-types';

const BookclubInfo = ({ bookclub }) => {
  const actBook = !bookclub.actualBook ? (<p>Not decided yet</p>) : (
    <p>{bookclub.actualBook.name}</p>
  );
  return (
    <div className="ui card">
      <div className="content">
        <h1 className="header">{bookclub.name}</h1>
      </div>
      <div className="content">
        <div className="description">
          {bookclub.description}
        </div>
      </div>
      <div className="extra content">
        <h1>Currently Reading: </h1>
        {actBook}
      </div>
    </div>
  );
};

BookclubInfo.propTypes = {
  bookclub: PropTypes.shape({
    id: PropTypes.number,
    name: PropTypes.string,
    description: PropTypes.string,
    actualBook: PropTypes.shape({
      id: PropTypes.string,
      name: PropTypes.string,
    }),
  }).isRequired,
};

export default BookclubInfo;
