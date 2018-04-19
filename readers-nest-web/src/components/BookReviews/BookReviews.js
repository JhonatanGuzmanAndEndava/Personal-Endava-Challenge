import React, { Component } from 'react';
import PropTypes from 'prop-types';

import gql from 'graphql-tag';
import { graphql } from 'react-apollo';


import './BookReviews.css';

class BookReviews extends Component {
  constructor(props) {
    super(props);

    this.state = {
      reviewText: '',
    };

    this.actUserId = localStorage.getItem('user-id');

    this.onChangeNewReview = this.onChangeNewReview.bind(this);
    this.onSubmitReview = this.onSubmitReview.bind(this);
  }

  onChangeNewReview(event) {
    this.setState({
      reviewText: event.target.value,
    });
  }

  onSubmitReview() {
    this.props.submitReview({
      variables: {
        bookId: this.props.bookId,
        newReview: {
          authorId: this.actUserId,
          reviewContent: this.state.reviewText,
        },
      },
    }).catch(error => (
      console.error(error)
    ));
  }

  reviewsToList() {
    return this.props.reviews.map(review => (
      <div className="comment" key={review.author.id}>
        <div className="content">
          <span className="author">{review.author.username}</span>
          <div className="metadata">
            <span className="date">{review.postedDate}</span>
          </div>
          <div className="text">
            {review.reviewContent}
          </div>
        </div>
      </div>
    ));
  }

  render() {
    return (
      <div className="ui comments fluid">
        <h3 className="ui dividing header">Reviews</h3>
        {this.reviewsToList()}
        <form className="ui reply form">
          <div className="field">
            <textarea placeholder="Type a message" value={this.state.reviewText} onChange={this.onChangeNewReview} />
          </div>
          <button className="ui blue labeled submit icon button" onClick={this.onSubmitReview}>
            <i className="icon edit" /> Add Review
          </button>
        </form>
      </div>
    );
  }
}

BookReviews.propTypes = {
  bookId: PropTypes.string.isRequired,
  reviews: PropTypes.arrayOf(PropTypes.shape({
    author: PropTypes.shape({
      id: PropTypes.string,
      username: PropTypes.string,
    }),
    content: PropTypes.string,
    postedDate: PropTypes.string,
  })).isRequired,
  submitReview: PropTypes.func.isRequired,
};

const CREATE_REVIEW = gql`
  mutation createReview($bookId:ID!, $newReview:ReviewCreateInput!){
    createReview(bookId:$bookId, newReview:$newReview) {
      author {
        id
        username
      }
      reviewContent
      postedDate
    }
  }
`;

export default graphql(CREATE_REVIEW, {
  name: 'submitReview',
})(BookReviews);
