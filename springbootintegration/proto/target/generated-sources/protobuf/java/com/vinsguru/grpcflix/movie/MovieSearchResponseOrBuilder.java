// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: movie-service.proto

package com.vinsguru.grpcflix.movie;

public interface MovieSearchResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:MovieSearchResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .MovieDto movie = 1;</code>
   */
  java.util.List<com.vinsguru.grpcflix.movie.MovieDto> 
      getMovieList();
  /**
   * <code>repeated .MovieDto movie = 1;</code>
   */
  com.vinsguru.grpcflix.movie.MovieDto getMovie(int index);
  /**
   * <code>repeated .MovieDto movie = 1;</code>
   */
  int getMovieCount();
  /**
   * <code>repeated .MovieDto movie = 1;</code>
   */
  java.util.List<? extends com.vinsguru.grpcflix.movie.MovieDtoOrBuilder> 
      getMovieOrBuilderList();
  /**
   * <code>repeated .MovieDto movie = 1;</code>
   */
  com.vinsguru.grpcflix.movie.MovieDtoOrBuilder getMovieOrBuilder(
      int index);
}
