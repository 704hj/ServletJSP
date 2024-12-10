package kr.spring.mvc10.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import kr.spring.mvc10.vo.PageRank;

//상속 받아야함
public class PageRanksView extends AbstractXlsView{

	@Override						//데이터가 map구조로 담겨있음	   ,시트 구조로 엑셀 파일 생성해줌
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//시트 생성
		HSSFSheet sheet = createFirstSheet((HSSFWorkbook)workbook);
		
		//열 이름 생성
		createColumnLabel(sheet);
		
		//시트에 데이터 표시하기
		List<PageRank> pageRanks = (List<PageRank>)model.get("pageRanks");
		int rowNum = 1;
		for(PageRank rank : pageRanks) {
			createPageRankRow(sheet, rank, rowNum++);
		}
		//HTTP 응답 메시지 헤더 설정
		String fileName = "pageRanks2024.xls";
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		
	}
	//시트 생성 : 내부에서만 사용할거니까 private
	private HSSFSheet createFirstSheet(HSSFWorkbook workbook) {
		HSSFSheet sheet = workbook.createSheet();
				  //sheet index, 이름
		workbook.setSheetName(0, "페이지 순위");
		//특정 컬럼에 넓이 지정/ 컬럼 인덱스, 넓이
		sheet.setColumnWidth(1, 256 * 20);
		
		return sheet;
	}
	//열이름 생성
	private void createColumnLabel(HSSFSheet sheet) {
		HSSFRow firstRow = sheet.createRow(0);//첫번째 행
		HSSFCell cell = firstRow.createCell(0);//첫번째 cell(열)
		cell.setCellValue("순위");
		
		cell = firstRow.createCell(1);//두번째 cell(열)
		cell.setCellValue("페이지");
		
				
	}
	//행 생성
	private void createPageRankRow(HSSFSheet sheet,PageRank rank,int rowNum) {
		HSSFRow row = sheet.createRow(rowNum);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(rank.getRank());
		
		cell = row.createCell(1);
		cell.setCellValue(rank.getPage());
	}
	
}





















