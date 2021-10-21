package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    protected Map<Long, Post> allPosts = new ConcurrentHashMap<>();
    protected long numberOfAllPosts;

    public List<Post> all() {
        if (allPosts.size() == 0) {
            return Collections.emptyList();
        }
        return allPosts.values().stream().toList();
    }

    public Optional<Post> getById(long id) {
        if (allPosts.containsKey(id)) {
            return Optional.of(allPosts.get(id));
        } else {
            return Optional.empty();
        }
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            Post savedPost = new Post(++numberOfAllPosts, post.getContent());
            allPosts.put(savedPost.getId(), savedPost);
            return savedPost;
        } else {
            if (allPosts.containsKey(post.getId())) {
                allPosts.put(post.getId(), post);
                return post;
            } else {
                return new Post(0, "Post can not be saved");
            }
        }
    }

    public void removeById(long id) {
        allPosts.entrySet().removeIf(entry -> entry.getKey().equals(id));
    }
}

