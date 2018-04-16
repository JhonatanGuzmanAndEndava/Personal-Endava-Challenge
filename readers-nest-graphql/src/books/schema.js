export const Schema = [`

    #input BookCreateInput {
    #    name:String!
    #    author:String!
#
    #    isbn10:String!
    #    isbn13:String!
#
    #    editorial:String!
    #    publishingDate:String!
    #    coverURL:String!
    #}
#
    #input BookUpdateInput {
    #    name:String
    #    author:String
#
    #    isbn10:String
    #    isbn13:String
#
    #    editorial:String
    #    publishingDate:String
    #    coverURL:String
    #}

    input ReviewCreateInput {
        authorId:ID!
        reviewContent:String!
    }

    input ReviewUpdateInput {
        authorId:ID!
        reviewContent:String!
    }
    
    type Book {
        id:ID!
        name:String!
        author:String!

        isbn10:String!
        isbn13:String!

        editorial:String!
        publishingDate:String!
        coverURL:String!

        reviews:[Review!]!

    }

    type Review {
        author:User!
        reviewContent:String!
        postedDate:String!
    }

    extend type Query {

        book(id:ID!):Book!
        books:[Book!]!

        reviews(bookId:ID!):[Review!]!        
    }

    extend type Mutation {

       createReview(bookId:ID!, newReview:ReviewCreateInput!):Review!
       updateReview(bookId:ID!, updatedReview:ReviewUpdateInput!):Review!
       deleteReview(bookId:ID!, authorId:ID!):Review!
       
    }
`];

export const Resolvers = {
  Query: {
    book(root, { id }, context) {
      return context.Books.findById(id);
    },
    books(root, args, context) {
      return context.Books.findAll();
    },
    reviews(root, { bookId }, context) {
      return context.Books.getReviews(bookId);
    },
  },
  Mutation: {
    createReview(root, { bookId, newReview }, context) {
      return context.Books.createReview(bookId, newReview);
    },
    updateReview(root, { bookId, updatedReview }, context) {
      return context.Books.updateReview(bookId, updatedReview);
    },
    deleteReview(root, { bookId, authorId }, context) {
      return context.Books.deleteReview(bookId, authorId);
    },
  },
  Book: {
    reviews(book) {
      return book.reviews;
    },
  },
  Review: {
    author(review, args, context) {
      return context.Users.findById(review.authorId);
    },
  },
};
