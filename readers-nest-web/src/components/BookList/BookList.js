import React, { Component } from 'react';
import PropTypes from 'prop-types';
import BookListElem from './BookListElem';

class BookList extends Component {
  listToBookElems() {
    return this.props.books.map(book => (<BookListElem book={book} key={book.id} />));
  }

  render() {
    return (
      <div className="ui middle aligned divided selection list">
        {this.listToBookElems()}
      </div>
    );
  }
}

BookList.propTypes = {
  books: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    author: PropTypes.string,
  })).isRequired,
};

export default BookList;
