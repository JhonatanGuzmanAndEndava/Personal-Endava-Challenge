import React, { Component } from 'react';
import PropTypes from 'prop-types';
import BookclubListElem from './BookclubListElem';

class BookclubList extends Component {
  listToBookclubElems() {
    return this.props.bookclubs.map(bookclub => (
      <BookclubListElem bookclub={bookclub} key={bookclub.id} />
    ));
  }

  render() {
    return (
      <div className="ui divided selection list">
        {this.listToBookclubElems()}
      </div>
    );
  }
}

BookclubList.propTypes = {
  bookclubs: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    author: PropTypes.string,
  })).isRequired,
};

export default BookclubList;
