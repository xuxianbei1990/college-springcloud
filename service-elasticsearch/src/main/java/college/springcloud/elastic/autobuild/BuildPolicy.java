package college.springcloud.elastic.autobuild;


import college.springcloud.elastic.EsCriteria;

public enum BuildPolicy {
	
	MUST{
		public void build(EsCriteria criteria, EsMark mark, Object fieldValue) {
			Object esFieldName = mark.field();
			criteria.must(esFieldName, fieldValue);
		}
	},
	
	MUST_KEY_WORD{
		public void build(EsCriteria criteria, EsMark mark, Object fieldValue) {
			Object esFieldName = mark.field();
			criteria.mustText(esFieldName, fieldValue);
		}
	},
	
	MATCH_TEXT{
		public void build(EsCriteria criteria, EsMark mark, Object fieldValue) {
			Object esFieldName = mark.field();
			criteria.fullTextMatch(fieldValue, esFieldName);
		}
	},

	PAGE_SIZE{
		public void build(EsCriteria criteria, EsMark mark, Object fieldValue) {
			criteria.page(null,fieldValue);
		}
	},

	PAGE_INDEX{
		public void build(EsCriteria criteria, EsMark mark, Object fieldValue) {
			criteria.page(fieldValue,null);
		}
	},

	SORT{
		public void build(EsCriteria criteria, EsMark mark, Object fieldValue) {
			criteria.sortBy(mark.field(),fieldValue);
		}
	},

	SOUCE_INCLUDE{
		public void build(EsCriteria criteria, EsMark mark, Object fieldValue) {
			criteria._sourceInclude(fieldValue);
		}
	};
	
	abstract public void build(EsCriteria criteria, EsMark mark, Object fieldValue);
}
