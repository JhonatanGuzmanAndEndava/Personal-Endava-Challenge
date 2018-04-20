import React, { Component } from 'react';
import PropTypes from 'prop-types';

import gql from 'graphql-tag';
import { withApollo, compose, graphql } from 'react-apollo';

const GET_USERS_EMAILS = gql`
  {
    users {
      id
      email
    }
  }
`;

class BookclubInfo extends Component {
  constructor(props) {
    super(props);

    this.state = {
      adminEmail: '',
      memberEmail: '',
    };

    this.bookclub = this.props.bookclub;

    this.onChange = this.onChange.bind(this);

    this.addMember = this.addMember.bind(this);
    this.addAdmin = this.addAdmin.bind(this);
  }

  onChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  addMember() {
    this.props.client.query({
      query: GET_USERS_EMAILS,
    }).then(({ data }) => {
      const foundUser = data.users.find(user => user.email === this.state.memberEmail);
      if (foundUser) {
        this.props.addMember({
          variables: {
            bookclubId: this.bookclub.id,
            memberId: foundUser.id,
          },
        });
      } else {
        throw new Error("That user doesn't exists");
      }
    }).catch(error => console.log(error));
  }

  addAdmin() {
    this.props.client.query({
      query: GET_USERS_EMAILS,
    }).then(({ data }) => {
      const foundUser = data.users.find(user => user.email === this.state.adminEmail);
      if (foundUser) {
        this.props.addAdmin({
          variables: {
            bookclubId: this.bookclub.id,
            adminId: foundUser.id,
          },
        });
      } else {
        throw new Error("That user doesn't exists");
      }
    }).catch(error => console.log(error));
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
            <button className="ui green icon button" onClick={this.addAdmin}>
              <i className="plus icon" />
              Add
            </button>
          </div>
          <div className="ui divider" />
          <div className="ui fluid action input">
            <input type="text" name="memberEmail" placeholder="Member email..." value={this.state.memberEmail} onChange={this.onChange} />
            <button className="ui green icon button" onClick={this.addMember}>
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
  addMember: PropTypes.func.isRequired,
  addAdmin: PropTypes.func.isRequired,
  client: PropTypes.shape({
    query: PropTypes.func,
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

