import { merge } from 'lodash';

import { Schema as booksSchema, Resolvers as booksResolvers } from './books/schema';
import { Schema as usersSchema, Resolvers as usersResolvers } from './users/schema';

import Books from './books/models';
import Users from './users/models';

const rootSchema = [`
  type Query {
    _ : Boolean
  }

  type Mutation {
    _ : Boolean
  }

  schema {
    query:Query
    mutation: Mutation
  }
`];

const getHeader = req => ({ header: req.response.get('Authorization') });


export const typeDefs = [...rootSchema, ...booksSchema, ...usersSchema];
export const resolvers = merge(booksResolvers, usersResolvers);
export const context = req => ({
  Users: new Users(getHeader(req)),
  Books: new Books(getHeader(req)),
});
