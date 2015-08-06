package com.wk.querytagger.adapter;

import com.wolterskluwer.services.docs.rsi.GetFolderById;
import com.wolterskluwer.services.docs.rsi.GetFolderByIdResponse;
import com.wolterskluwer.services.docs.rsi.GetUserFolders;
import com.wolterskluwer.services.docs.rsi.GetUserFoldersResponse;
import com.wolterskluwer.services.types.common.SecurityToken;

public interface RsiAdapter {
	GetUserFoldersResponse getUserFolders(GetUserFolders foldersRequest);
	
	GetFolderByIdResponse getFolderById(GetFolderById folderRequest);
}
