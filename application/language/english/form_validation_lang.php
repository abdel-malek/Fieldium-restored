<?php
$lang['alpha']				= "%s may only contain alphabetical characters.";
$lang['alpha_dash']			= "%s may only contain alpha-numeric characters, underscores, and dashes.";
$lang['alpha_dash_space']	= "%s may only contain alpha-numeric characters, underscores, dashes, and spaces.";
$lang['alpha_dash_period']	= "%s may only contain alpha-numeric characters, underscores, dashes, and periods.";
$lang['alpha_numeric']		= "%s may only contain alpha-numeric characters.";
$lang['boolean']			= "%s must be a boolean value.";
$lang['enum']				= "%s must be one of: %s.";
$lang['limithtml']			= "%s can only contain the following HTML tags: %s. If you want to use angle brackets < in your text, but not HTML please try &amp;lt; to replace < and &amp;gt; to replace >.";
$lang['exact_length']		= "%s must be exactly %s characters in length.";
$lang['hex_color']			= "%s must contain a valid hex color code.";
$lang['integer']			= "%s must contain an integer.";
$lang['is_natural']			= "%s must contain only positive numbers.";
$lang['is_natural_no_zero']	= "%s must contain a number greater than zero.";
$lang['is_numeric']			= "%s must contain only numeric characters.";
$lang['is_unique']			= "%s must be unique.";
$lang['matches']			= "%s does not match the %s field.";
$lang['max_length']			= "%s cannot exceed %s characters in length.";
$lang['min_length']			= "%s must be at least %s characters in length.";
$lang['numeric']			= "%s must contain only numbers.";
$lang['greater_than']		= "%s must be greater than: %s";
$lang['less_than']			= "%s must be less than: %s";
$lang['regex']				= "%s must match the regular expression `%s`.";
$lang['required']			= "%s is required.";
$lang['unique']				= "%s must be unique.";
$lang['valid_base64']		= "%s may only contain characters in the base64 character set (alpha-numeric, slash, plus, and equals).";
$lang['valid_email']		= "%s must contain a valid email address.";
$lang['unique_email']		= "%s must contain a unique email address.";
$lang['valid_emails']		= "%s must contain all valid email addresses.";
$lang['valid_ip']			= "%s must contain a valid IP.";
$lang['valid_url']			= "%s must contain a valid URL.";
$lang['invalid_xss_check']  = 'The data you submitted did not pass our security check. If you did not intend to submit this form, please <a href="%s">click here</a> and no settings will be changed.';
$lang['no_html']  			= '%s cannot contain HTML.';
$lang['invalid_path']		= 'This path is either invalid or not writable.';
// Legacy form validation lib
$lang['file_exists']		= $lang['invalid_path'];
$lang['writable']			= $lang['invalid_path'];
// special and legacy things
$lang['isset']				= "The %s field must have a value.";
$lang['auth_password']		= "The password entered is incorrect.";
// EOF