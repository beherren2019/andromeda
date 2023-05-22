package com.galaxy.andromeda.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOwnerVO {

    private String login;

    private Long id;

    private String nodeId;

    private String gravatarId;

    private String type;

    private boolean siteAdmin;

    private OwnerLinksVO urls;
}
