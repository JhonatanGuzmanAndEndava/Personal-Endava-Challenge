import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { Query } from 'react-apollo';
import gql from 'graphql-tag';

import BookInfo from '../components/BookInfo/BookInfo';
import BookReviews from '../components/BookReviews/BookReviews';

class BookInfoPageContainer extends Component {
  constructor(props) {
    super(props);

    this.book = props.book;
  }
  render() {
    return (
      <div className="ui container">
        <BookInfo book={this.book} />
        <BookReviews bookId={this.book.id} reviews={this.book.reviews} />
      </div>
    );
  }
}

BookInfoPageContainer.propTypes = {
  book: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    author: PropTypes.string,
    editorial: PropTypes.string,
    publishingDate: PropTypes.string,
    reviews: PropTypes.arrayOf(PropTypes.shape({
      author: PropTypes.shape({
        id: PropTypes.string,
        username: PropTypes.string,
      }),
      reviewContent: PropTypes.string,
      postedDate: PropTypes.string,
    })),
  }).isRequired,
};

const GET_BOOK = gql`
  query book($id:ID!){
    book(id:$id){
      id
      name
      author
      editorial
      publishingDate
      reviews {
        author {
          id
          username
        }
        reviewContent
        postedDate
      }
    }
  }
`;

const BookInfoPage = ({ match }) => (
  <Query query={GET_BOOK} variables={{ id: match.params.id }}>
    {({ loading, data }) => {
      if (loading) {
        return (<div className="ui active centered inline text loader">Loading</div>);
      }
      return (<BookInfoPageContainer book={data.book} />);
    }}
  </Query>
);

BookInfoPage.propTypes = {
  match: PropTypes.shape({
    params: PropTypes.shape({
      id: PropTypes.string,
    }),
  }).isRequired,
};

export default BookInfoPage;
