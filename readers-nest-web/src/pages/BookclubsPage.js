import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { Query } from 'react-apollo';
import gql from 'graphql-tag';

import BookclubList from '../components/BookclubList/BookclubList';

class BookclubsPageContainer extends Component {
  constructor(props) {
    super(props);

    this.bookclubs = this.props.bookclubs;
  }

  render() {
    return (
      <div className="ui container">
        <BookclubList bookclubs={this.bookclubs} />
      </div>
    );
  }
}

BookclubsPageContainer.propTypes = {
  bookclubs: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.description,
    description: PropTypes.string,
  })).isRequired,
};

const GET_BOOKCLUBS = gql`
  {
    bookclubs {
      id
      name
      description
    }
  }
`;

const BooksclubsPage = () => (
  <Query query={GET_BOOKCLUBS}>
    {({ loading, data }) => {
      if (loading) {
        return (<div className="ui active centered inline text loader">Loading</div>);
      }
      return (<BookclubsPageContainer bookclubs={data.bookclubs} />);
    }}
  </Query>
);

export default BooksclubsPage;
