import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { Query } from 'react-apollo';
import gql from 'graphql-tag';

import BookclubInfo from '../components/BookclubInfo/BookclubInfo';
import MessageList from '../components/MessageList/MessageList';

class BookclubInfoPageContainer extends Component {
  constructor(props) {
    super(props);

    this.bookclub = props.bookclub;
  }
  render() {
    return (
      <div className="ui container grid">
        <div className="five wide column">
          <BookclubInfo bookclub={this.bookclub} />
        </div>
        <div className="eleven wide column">
          <MessageList bookclubId={this.bookclub.id} messages={this.bookclub.messages} />
        </div>
      </div>
    );
  }
}

BookclubInfoPageContainer.propTypes = {
  bookclub: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    description: PropTypes.string,
    actualBook: PropTypes.shape({
      id: PropTypes.string,
      name: PropTypes.string,
    }),
    messages: PropTypes.arrayOf(PropTypes.shape({
      author: PropTypes.shape({
        id: PropTypes.string,
        username: PropTypes.string,
      }),
      contentMessage: PropTypes.string,
      publishedDate: PropTypes.string,
    })),
  }).isRequired,
};

const GET_BOOKCLUB = gql`
  query bookclub($id: ID!) {
    bookclub(id: $id) {
      id
      name
      description
      actualBook {
        id
        name
      }
      messages {
        author {
          username
        }
        contentMessage
        publishedDate
      }
    }
  }
`;

const BookclubInfoPage = ({ match }) => (
  <Query query={GET_BOOKCLUB} variables={{ id: match.params.id }}>
    {({ loading, data }) => {
      if (loading) {
        return (<div className="ui active centered inline text loader">Loading</div>);
      }
      return (<BookclubInfoPageContainer bookclub={data.bookclub} />);
    }}
  </Query>
);

BookclubInfoPage.propTypes = {
  match: PropTypes.shape({
    params: PropTypes.shape({
      id: PropTypes.string,
    }),
  }).isRequired,
};

export default BookclubInfoPage;
