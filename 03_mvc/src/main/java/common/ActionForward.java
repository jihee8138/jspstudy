package common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data  // getter + setter + toString + equals ... 등이 들어있음
@Builder

public class ActionForward {
	private String view;
	private boolean isRedirect;
}
