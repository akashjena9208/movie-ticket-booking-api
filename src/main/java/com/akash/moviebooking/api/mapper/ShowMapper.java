package com.akash.moviebooking.api.mapper;


import com.akash.moviebooking.api.dto.ShowResponse;
import com.akash.moviebooking.api.entity.Show;
import org.springframework.stereotype.Component;

//@Component
//public class ShowMapper {
//
//    public ShowResponse showResponseMapper(Show show) {
//        if (show == null)
//            return null;
//
//        return ShowResponse.builder()
//                .showId(show.getShowId())
//                .startsAt(show.getStartsAt())
//                .endsAt(show.getEndsAt())
//                .build();
//    }
//
//}
//

//............................................................................................................





@Component
public class ShowMapper {

    public ShowResponse showResponseMapper(Show show) {
        if (show == null) return null;

        return ShowResponse.builder()
                .showId(show.getShowId())
                .startsAt(show.getStartsAt())
                .endsAt(show.getEndsAt())
                .screenId(show.getScreen().getScreenId())   // ✅ map screenId
                .screenType(show.getScreen().getScreenType()) // ✅ map screenType
                .build();
    }
}



//package com.akash.moviebooking.api.mapper;
//
//import com.akash.moviebooking.api.dto.ShowResponse;
//import com.akash.moviebooking.api.entity.Show;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ShowMapper {
//
//    public ShowResponse showResponseMapper(Show show) {
//        if (show == null)
//            return null;
//
//        return ShowResponse.builder()
//                .showId(show.getShowId())
//                .startsAt(show.getStartsAt())
//                .endsAt(show.getEndsAt())
//                .screenId(show.getScreen() != null ? show.getScreen().getScreenId() : null)
//                .screenType(show.getScreen() != null ? show.getScreen().getScreenType() : null)
//                .build();
//    }
//}
