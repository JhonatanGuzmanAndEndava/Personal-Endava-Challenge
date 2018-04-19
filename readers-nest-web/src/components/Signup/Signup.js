import React, { Component } from 'react';
import PropTypes from 'prop-types';

import gql from 'graphql-tag';
import { graphql } from 'react-apollo';

import { withRouter } from 'react-router-dom';

class Signup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      username: '',
      firstName: '',
      lastName: '',
    };

    this.onChange = this.onChange.bind(this);
    this.onSignup = this.onSignup.bind(this);
  }

  onChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  onSignup() {
    this.props.signup({
      variables: {
        newUser: this.state,
      },
    }).then(({ data }) => {
      localStorage.setItem('user-id', data.signup.userId);
      localStorage.setItem('token', data.signup.token);
      localStorage.setItem('refresh-token', data.signup.refreshToken);
      this.props.history.push('/');
    }).catch(error => (
      console.log(error)
    ));
  }

  render() {
    return (
      <div className="ui container form">
        <h1 className="header">Sign Up</h1>
        <div className="field">
          <label>Email</label>
          <input type="text" name="email" placeholder="email@email.com" value={this.state.email} onChange={this.onChange} />
        </div>
        <div className="field">
          <label>Password</label>
          <input type="password" name="password" placeholder="Password" value={this.state.password} onChange={this.onChange} />
        </div>
        <div className="field">
          <label>username</label>
          <input type="text" name="username" placeholder="Username" value={this.state.username} onChange={this.onChange} />
        </div>
        <div className="field">
          <label>First Name</label>
          <input type="text" name="firstName" placeholder="First Name" value={this.state.firstName} onChange={this.onChange} />
        </div>
        <div className="field">
          <label>Last Name</label>
          <input type="text" name="lastName" placeholder="Last Name" value={this.state.lastName} onChange={this.onChange} />
        </div>
        <button className="fluid ui primary button" onClick={this.onSignup}>Sign In</button>
      </div>
    );
  }
}

Signup.propTypes = {
  signup: PropTypes.func.isRequired,
  history: PropTypes.shape({
    push: PropTypes.func,
  }).isRequired,
};

const SIGN_UP = gql`
  mutation signup($newUser: UserCreateInput!) {
    signup(newUser: $newUser) {
      userId
      token
      refreshToken
    }
  }
`;

export default graphql(SIGN_UP, {
  name: 'signup',
})(withRouter(Signup));

