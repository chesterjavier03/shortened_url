package com.project.service;

import com.project.entity.Url;
import com.project.utility.ShortenedUrlProcessorUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chesterjavier.
 */
@Service
@Slf4j
@Repository
@Transactional
public class UrlService {

  @Autowired
  private EntityManager em;

  public void addUrl(Url url) {
    log.info("[UrlService] - Adding Urls [original and shortened] into the database....");
    em.persist(url);
    em.flush();
  }

  public JSONObject fetchAllUrls() {
    log.info("[UrlService] - Fetching all Urls in the database....");
    JSONObject json = new JSONObject();
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Url> query = builder.createQuery(Url.class);
    Root<Url> Url = query.from(com.project.entity.Url.class);
    query.select(Url);
    TypedQuery<com.project.entity.Url> result = em.createQuery(query);
    List<com.project.entity.Url> resultList = result.getResultList();
    json.put("data", resultList);
    json.put("totalRows", resultList.size());
    return json;
  }

  public Url fetchUrlByOriginalUrl(String originalUrl) {
    log.info("[UrlService] - Fetching data if originalUrl exist in the database - [{}]....", originalUrl);
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Url> query = builder.createQuery(Url.class);
    Root<Url> urlRoot = query.from(Url.class);
    Predicate urlOriginalName = builder.equal(urlRoot.get("originalUrl"), originalUrl);
    query.select(urlRoot).where(urlOriginalName);
    TypedQuery<Url> result = em.createQuery(query);
    return !result.getResultList().isEmpty() ? result.getResultList().get(0) : null;
  }

  public String processUrl(String url, HttpServletRequest request) {
    log.info("[UrlService] - Process url for shortening [{}]....", url);
    JSONObject json = new JSONObject();
    if (StringUtils.isNotBlank(url)) {
      String result = ShortenedUrlProcessorUtility.convertToShortenedUrl(url);
      String req = request.getRequestURL().toString();
      String prefix = req.substring(0, req.indexOf(request.getRequestURI(), "http://".length()));
      json.put("shortened_url", prefix + "/" + result);
      if (null == fetchUrlByOriginalUrl(url)) {
        addUrl(new Url(url, prefix + "/" + result));
      }
    }
    return json.toJSONString();
  }
}
