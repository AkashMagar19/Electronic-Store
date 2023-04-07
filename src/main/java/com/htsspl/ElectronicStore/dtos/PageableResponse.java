package com.htsspl.ElectronicStore.dtos;

import lombok.*;

import javax.persistence.Table;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Table(name ="users")
public class PageableResponse <T>{

        private List<T> content;
        private int pageNumber;
        private int pageSize;
        private long totalElement;
        private int totalPages;
        private boolean lastPage;

        }
