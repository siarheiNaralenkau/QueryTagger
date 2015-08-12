package com.wk.querytagger.adapter;

import com.wolterskluwer.services.docs.rsi.AddItemsToFolder;
import com.wolterskluwer.services.docs.rsi.AddItemsToFolderResponse;
import com.wolterskluwer.services.docs.rsi.CreateFolder;
import com.wolterskluwer.services.docs.rsi.CreateFolderResponse;
import com.wolterskluwer.services.docs.rsi.GetDocumentMetadata;
import com.wolterskluwer.services.docs.rsi.GetDocumentMetadataResponse;
import com.wolterskluwer.services.docs.rsi.GetExtendedMetadataForDocuments;
import com.wolterskluwer.services.docs.rsi.GetExtendedMetadataForDocumentsResponse;
import com.wolterskluwer.services.docs.rsi.GetFolderById;
import com.wolterskluwer.services.docs.rsi.GetFolderByIdResponse;
import com.wolterskluwer.services.docs.rsi.GetUserFolders;
import com.wolterskluwer.services.docs.rsi.GetUserFoldersResponse;

public interface RsiAdapter {
	GetUserFoldersResponse getUserFolders(GetUserFolders foldersRequest);
	
	GetFolderByIdResponse getFolderById(GetFolderById folderRequest);
	
	GetDocumentMetadataResponse getDocumentMeta(GetDocumentMetadata docMetaRequest);
	
	CreateFolderResponse createFolder(CreateFolder createFolderRequest);
	
	AddItemsToFolderResponse addItemsToFolder(AddItemsToFolder AddItemsReq);
	
	GetExtendedMetadataForDocumentsResponse getDocExtendedMeta(GetExtendedMetadataForDocuments extMetaRequest);
}
