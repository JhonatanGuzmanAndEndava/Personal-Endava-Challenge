import React, { Component } from 'react';
import PropTypes from 'prop-types';

import gql from 'graphql-tag';
import { withApollo, compose, graphql } from 'react-apollo';

class BookclubInfo extends Component {
  constructor(props) {
    super(props);

    this.state = {
      adminEmail: '',
      memberEmail: '',
    };

    this.bookclub = this.props.bookclub;

    this.onChange = this.onChange.bind(this);
  }

  onChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  render() {
    const actBook = !this.bookclub.actualBook ? (<p>Not decided yet</p>) : (
      <p>{this.bookclub.actualBook.name}</p>
    );
    return (
      <div className="ui card">
        <div className="content">
          <h1 className="header">{this.bookclub.name}</h1>
        </div>
        <div className="content">
          <div className="description">
            {this.bookclub.description}
          </div>
        </div>
        <div className="content">
          <h3>Currently Reading: </h3>
          {actBook}
        </div>
        <div className="extra content">
          <div className="ui fluid action input">
            <input type="text" name="adminEmail" placeholder="Admin email..." value={this.state.adminEmail} onChange={this.onChange} />
            <button className="ui green icon button">
              <i className="plus icon" />
              Add
            </button>
          </div>
          <div className="ui divider" />
          <div className="ui fluid action input">
            <input type="text" name="memberEmail" placeholder="Member email..." value={this.state.memberEmail} onChange={this.onChange} />
            <button className="ui green icon button">
              <i className="plus icon" />
              Add
            </button>
          </div>
        </div>
      </div>
    );
  }
}

BookclubInfo.propTypes = {
  bookclub: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    description: PropTypes.string,
    actualBook: PropTypes.shape({
      id: PropTypes.string,
      name: PropTypes.string,
    }),
  }).isRequired,
};

const ADD_MEMBER = gql`
  mutation addMember($bookclubId:ID!, $memberId:ID!){
    addBookclubMember(bookclubId:$bookclubId, memberId:$memberId)
  }
`;

const ADD_ADMIN = gql`
  mutation addAdmin($bookclubId:ID!, $adminId:ID!) {
    addBookclubAdmin(bookclubId:$bookclubId, memberId:$adminId)
  }
`;

export default compose(
  withApollo,
  graphql(ADD_MEMBER, {
    name: 'addMember',
  }),
  graphql(ADD_ADMIN, {
    name: 'addAdmin',
  }),
)(BookclubInfo);

