package com.galaxy.andromeda.vo.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreItemSearchListVO {

     private long totalPages;

     private long totalCount;

     private int pageNo;

     private List<StoreItemVO> storeItems;

}
