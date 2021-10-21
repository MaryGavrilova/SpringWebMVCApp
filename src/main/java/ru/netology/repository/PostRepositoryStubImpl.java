package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    protected Map<Long, Post> allPosts = new ConcurrentHashMap<>();
    protected long numberOfAllPosts;

    public List<Post> all() {
        if (allPosts.size() == 0) {
            return Collections.emptyList();
        } else {
            return allPosts.values().stream().toList()
                    .stream().filter(post -> !post.isRemoved()).collect(Collectors.toList());
        }

    }

    public Optional<Post> getById(long id) {
        if (allPosts.containsKey(id)) {
            if (!allPosts.get(id).isRemoved()) {
                return Optional.of(allPosts.get(id));
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public Optional<Post> save(Post post) {
        if (post.getId() == 0) {
            Post savedPost = new Post(++numberOfAllPosts, post.getContent());
            allPosts.put(savedPost.getId(), savedPost);
            return Optional.of(savedPost);
        } else {
            if (allPosts.containsKey(post.getId())) {
                if (!allPosts.get(post.getId()).isRemoved()) {
                    allPosts.put(post.getId(), post);
                    return Optional.of(post);
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.empty();
            }
        }
    }

    public void removeById(long id) {
        if (allPosts.containsKey(id)) {
            allPosts.get(id).setRemoved(true);
        }
    }
}

