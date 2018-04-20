import { GraphQLServer } from 'graphql-yoga';
import { typeDefs, resolvers, context } from './schema';

const server = new GraphQLServer({
  typeDefs,
  resolvers,
  context,
});

server.start();
