package cl.zpricing.avant.model.loadmanager;

import java.util.List;

public class LayoutPorCategoria extends Layout {
	private List<LayoutAreaPorCategoria> layoutAreaPorCategoria;

	public List<LayoutAreaPorCategoria> getLayoutAreaPorCategoria() {
		return layoutAreaPorCategoria;
	}

	public void setLayoutAreaPorCategoria(
			List<LayoutAreaPorCategoria> layoutAreaPorCategoria) {
		this.layoutAreaPorCategoria = layoutAreaPorCategoria;
	}
}
