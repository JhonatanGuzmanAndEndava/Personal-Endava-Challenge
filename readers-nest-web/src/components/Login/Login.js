import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { withRouter } from 'react-router-dom';

import { graphql } from 'react-apollo';
import gql from 'graphql-tag';

class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      email: '',
      password: '',
    };

    this.onChange = this.onChange.bind(this);
    this.onLogin = this.onLogin.bind(this);

    this.onLogin = this.onLogin.bind(this);
  }

  onChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  onLogin() {
    this.props.login({
      variables: {
        credentials: this.state,
      },
    }).then(({ data }) => {
      localStorage.setItem('user-id', data.login.userId);
      localStorage.setItem('token', data.login.token);
      localStorage.setItem('refresh-token', data.login.refreshToken);
      this.props.history.push('/');
    }).catch(error => (
      console.log(error)
    ));
  }

  render() {
    return (
      <div className="ui container form">
        <h1 className="header">Login</h1>
        <div className="field">
          <label>Email</label>
          <input type="text" name="email" placeholder="email@email.com" value={this.state.email} onChange={this.onChange} />
        </div>
        <div className="field">
          <label>Password</label>
          <input type="password" name="password" placeholder="Type your passsword..." value={this.state.password} onChange={this.onChange} />
        </div>
        <button className="fluid ui primary button" onClick={this.onLogin}>Login</button>
      </div>
    );
  }
}

Login.propTypes = {
  login: PropTypes.func.isRequired,
  history: PropTypes.shape({
    push: PropTypes.func,
  }).isRequired,
};

const LOGIN = gql`
  mutation login($credentials: UserCredentialsInput!) {
    login(credentials:$credentials) {
      userId
      token
      refreshToken
    }
  }
`;

export default graphql(LOGIN, {
  name: 'login',
})(withRouter(Login));
