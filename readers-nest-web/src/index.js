import React from 'react';
import ReactDOM from 'react-dom';

import { BrowserRouter } from 'react-router-dom';

import ApolloClient from 'apollo-boost';

import { ApolloProvider } from 'react-apollo';

import 'semantic-ui-css/semantic.min.css';
import './index.css';
import App from './components/App/App';
import registerServiceWorker from './registerServiceWorker';

const client = new ApolloClient({
  uri: 'http://35.172.164.34:4000',
  request: (operation) => {
    const token = localStorage.getItem('token');
    operation.setContext({
      headers: {
        authorization: token ? `Bearer ${token}` : '',
      },
    });
  },
});

ReactDOM.render(
  (
    <BrowserRouter>
      <ApolloProvider client={client}>
        <App />
      </ApolloProvider>
    </BrowserRouter>
  ), document.getElementById('root'),
);
registerServiceWorker();
