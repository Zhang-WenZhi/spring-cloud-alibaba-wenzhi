package com.wenzhi.leetcode_service.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class GraphqlTest {
    public static void main(String[] args) {
        String schema = "type Query{hello: String}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{hello}");

        System.out.println(executionResult.getData().toString());
        // Prints: {hello=world}
    }

    private void example() {
        // 1. 定义schema
        GraphQLObjectType querytype =
                GraphQLObjectType.newObject()
                        .name("Query")
                        .field(field -> field
                                .name("hello")
                                .type(Scalars.GraphQLString)
                                .dataFetcher(env -> "hello world!"))
                        .build();

        // 2. 构建执行引擎
        GraphQL graphQL = GraphQL.newGraphQL(
                GraphQLSchema.newSchema()
                        .query(querytype)
                        .build()
        ).build();

        // 执行查询
        ExecutionResult result = graphQL.execute("{ hello }");
    }
}
