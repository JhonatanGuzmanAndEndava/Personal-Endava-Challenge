import React, { Component } from 'react';
import PropTypes from 'prop-types';

import gql from 'graphql-tag';
import { Query } from 'react-apollo';

import BookList from '../components/BookList/BookList';

class BooksPageContainer extends Component {
  constructor(props) {
    super(props);

    this.books = props.books;
  }

  render() {
    return (
      <div className="ui container">
        <BookList books={this.books} />
      </div>
    );
  }
}

BooksPageContainer.propTypes = {
  books: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    author: PropTypes.string,
  })).isRequired,
};

const GET_BOOKS = gql`
  {
    books {
      id
      name
      author
    }
  }
`;

const BooksPage = () => (
  <Query query={GET_BOOKS}>
    {({ loading, data }) => {
      if (loading) {
        return (<div className="ui active centered inline text loader">Loading</div>);
      }
      return (<BooksPageContainer books={data.books} />);
    }}
  </Query>
);

export default BooksPage;
