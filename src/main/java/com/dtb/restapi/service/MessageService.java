package com.dtb.restapi.service;

import java.util.List;

public interface MessageService {
	List<String> getMessages(List<String> source);
}
